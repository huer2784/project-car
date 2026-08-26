import { getCars, deleteCar } from "../api/carapi";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";

import Box from '@mui/material/Box';
import { IconButton, Snackbar, Stack } from "@mui/material";
import { DataGrid, 
        type GridColDef, 
        type GridRenderCellParams,
      } from '@mui/x-data-grid';
import { useState } from "react";
import AddCar from "./AddCar";
import EditCar from "./EditCar";
import CustomToolbar from "./CustomToolbar";
import DeleteIcon from '@mui/icons-material/Delete';
import Tooltip from "@mui/material/Tooltip";

type CarlistProps = {
    logout?: () => void;
}

const Carlist = ({logout} : CarlistProps) => {
    const [open, setOpen] = useState(false);
    const queryClient = useQueryClient();

    const {data, error, isSuccess} = useQuery({
        queryKey : ["cars"],
        queryFn : getCars
    });

    const {mutate} = useMutation({
        mutationFn: deleteCar,
        onSuccess: () => {
            setOpen(true);
            queryClient.invalidateQueries({
                queryKey: ['cars'],
            });
        },
        onError: (err) => {
            console.log(err);
        }
    });
    /*
        -.MUI DATA GRID 컬럼 너비 조절옵션은 컬럼자체에 flex:숫자로 비율을 주거나
        -.DataGrid 속성에 autosizeOptions 설정
        autosizeOptions={{
                                includeHeaders: true,
                                includeOutliers: true,
                                expand: true,
                        }}
    */
    const columns: GridColDef[] = [
        { field: 'brand', headerName: 'Brand',flex: 1},
        { field: 'model', headerName: 'Model',flex: 1},
        { field: 'color', headerName: 'Color',flex: 1},
        { field: 'registationNumber', headerName: 'Reg.No',flex: 1},
        { field: 'modelYear', headerName: 'Model Year',flex: 1},
        { field: 'price', headerName: 'Price',flex: 1},
        { field: 'edit', headerName: '',flex: 1 , sortable:false, filterable:false, disableColumnMenu:true,
            renderCell: (params: GridRenderCellParams) => (
                <EditCar cardata={params.row}/>
            )
            
        },
        { field: 'delete', headerName: '',flex: 1 , sortable:false, filterable:false, disableColumnMenu:true,
            renderCell: (params: GridRenderCellParams) => (
                <Tooltip title="Delete car">
                    <IconButton aria-label="delete" size="small" onClick={() => handleDelete(params)}>
                        <DeleteIcon fontSize="small" />
                    </IconButton>
                </Tooltip>
            )
            
        },
    ];

    const handleDelete = (params: GridRenderCellParams) => {
        if(window.confirm(`삭제하시겠습니까? ( ${params.row.brand} ${params.row.model} )`)){mutate(params.row._links.car.href)}
    }

    if(error){
        return <span>Error when fetching cars...</span>
    }
    if(!isSuccess){
        return <span>Loading...</span>
    }

    return (
        <>
            <Box sx={{ height: 800, width: '100%' }}>
                <Stack direction="row" sx={{alignItems:"center", justifyContent:"space-between"}}>
                    <AddCar/>
                    <button onClick={logout}>로그아웃</button>

                </Stack>
                <DataGrid rows={data} 
                        columns={columns} 
                        disableRowSelectionOnClick={false}
                        //셀선택시 border-line 유무
                        sx={{
                                '& .MuiDataGrid-cell:focus, & .MuiDataGrid-cell:focus-within': {
                                outline: 'none',
                                },
                                '& .MuiDataGrid-columnHeader:focus, & .MuiDataGrid-columnHeader:focus-within': {
                                outline: 'none',
                                },
                            }}
                        getRowId={row => row._links.self.href}
                        slots={{ toolbar: CustomToolbar }}
                        showToolbar
                    
                    />
            </Box>
            <Snackbar
                open={open}
                autoHideDuration={2000}
                onClose={() => setOpen(false)}
                message="Car 삭제됨"
            />
        </>
    );
}

export default Carlist;