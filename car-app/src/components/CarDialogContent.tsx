import type React from "react";
import type { Car } from "../types/type-car";

import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import { NumericFormat } from 'react-number-format';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';

type DialogFormProps = {
    car: Car;
    handleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handleChangeNumber: (name: string, value: number) => void;
}

const CarDialogContent = ({car, handleChange, handleChangeNumber}:DialogFormProps) => {
    return (
        <>
            <DialogContent
                sx={{
                    '& > :not(style)': {
                    width: '100%',
                    },
                }}
            >
                <TextField label="브랜드" name="brand" variant="standard" value={car.brand} onChange={handleChange}/><br />
                <TextField label="모델" name="model" variant="standard" value={car.model} onChange={handleChange}/><br />
                <TextField label="색상" name="color" variant="standard" value={car.color} onChange={handleChange}/><br />
                <TextField label="등록번호" name="registationNumber" variant="standard" value={car.registationNumber} onChange={handleChange}/><br />
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker
                        label="연도"
                        maxDate={dayjs()}
                        openTo="year"
                        views={['year']}
                        yearsOrder="desc"
                        sx={{ mt: 1.5, mb: 1.5, ml: -0.5, maxWidth: '200px'}}
                        value={dayjs(String(car.modelYear), 'YYYY')}
                        onChange={(newValue) => {
                            handleChangeNumber('modelYear', newValue?.year() ?? 0);
                        }}
                        />
                </LocalizationProvider>
                <br />
                <NumericFormat
                    value={car.price}
                    onValueChange={(values) => {
                        handleChangeNumber('price', values.floatValue ?? 0);
                    }}
                    customInput={TextField}
                    thousandSeparator
                    prefix=""
                    variant="standard"
                    label="가격"
                    name="price"
                />
                
            </DialogContent>
        </>
    );
}

export default CarDialogContent;