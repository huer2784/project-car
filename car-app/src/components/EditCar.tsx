import { useState } from "react";
import {type Car } from "../types/type-car";
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import DialogContentText from '@mui/material/DialogContentText';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import CarDialogContent from "./CarDialogContent";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateCar } from "../api/carapi";
import EditIcon from '@mui/icons-material/Edit';
import { IconButton } from "@mui/material";
import Tooltip from "@mui/material/Tooltip";

    type EditCarProps = {
        cardata: Car;
    };

const EditCar = ({cardata}: EditCarProps) => {
    const [open, setOpen] = useState(false);
    const [car, setCar] = useState<Car>({
        brand:'',
        model:'',
        color:'',
        registationNumber:'',
        modelYear:2026,
        price:0
    });

    const queryClient = useQueryClient();

    const {mutate} = useMutation({
        mutationFn : updateCar,
        onSuccess : () => {
            queryClient.invalidateQueries({
                queryKey: ['cars'],
            });
        },
        onError : (err) => {
            console.log(err);
        }
    });

    const handleClickOpen = () => {
        setCar({
            brand: cardata.brand,
            model: cardata.model,
            color: cardata.color,
            registationNumber: cardata.registationNumber,
            modelYear: cardata.modelYear,
            price: cardata.price,
            _links: cardata._links
        });
        
        setOpen(true);

        
    };

    const handleClose = () => {
        setOpen(false);
    }

    const handleChange = (event : React.ChangeEvent<HTMLInputElement>) => {
        setCar({...car, [event.target.name] : event.target.value});
    }

    const handleChangeNumber = (name: string, value: number) => {
        setCar({...car, [name] : value});
    }

    const handleSave = () => {
        mutate(car);
        setOpen(false);
    }

    return (
         <>
            <Tooltip title="Edit car">
                <IconButton aria-label="delete" size="small" onClick={handleClickOpen}>
                    <EditIcon fontSize="small" />
                </IconButton>
            </Tooltip>
            <Box>
                <Dialog
                    open={open}
                    slotProps={{
                        paper: {
                            sx: {
                                width: '400px',
                            },
                        },
                    }}
                >
                    <DialogTitle>새 차</DialogTitle>
                    <CarDialogContent car={car} handleChange={handleChange} handleChangeNumber={handleChangeNumber}/>
                    <DialogContentText></DialogContentText>                
                    <DialogActions>
                        <Button onClick={handleSave} autoFocus>
                            저장
                        </Button>
                        <Button onClick={handleClose}>취소</Button>
                    </DialogActions>
                </Dialog>
            </Box>
         </>
    );

}

export default EditCar;