import React, { useState } from "react";

import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import DialogContentText from '@mui/material/DialogContentText';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import { type Car } from "../types/type-car";
import { addCar } from "../api/carapi";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import CarDialogContent from "./CarDialogContent";

const AddCar = () => {
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
        mutationFn : addCar,
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
        setOpen(true);
    }

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
        setCar({
            brand:'',
            model:'',
            color:'',
            registationNumber:'',
            modelYear:0,
            price:0
        });
        handleClose();
    }

    return (
        <>
            <Box
                sx={{ '& > :not(style)': { mt: 1, mb: 1, width: '10ch' } }}
            >
                <Button variant="outlined" onClick={handleClickOpen}>
                    새 차
                </Button>
            </Box>
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

export default AddCar;