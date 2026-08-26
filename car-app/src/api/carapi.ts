
import type {Car, CarApiResponse } from "../types/type-car";
import axios, {type AxiosRequestConfig} from "axios";

const getAxiosConfig = (): AxiosRequestConfig => {
    const jwtToekn = sessionStorage.getItem("jwt");

    return {
        headers : {
                        'Content-Type' : 'application/json',
                        'Authorization' : jwtToekn
                    }
    }
}

export const getCars = async() : Promise<Car[]> => {
    const response = await axios.get<CarApiResponse>(`${import.meta.env.VITE_API_URL}/api/cars`, getAxiosConfig());
    //console.log("getCars data:", response.data._embedded.cars);
    return response.data._embedded.cars;
}

export const deleteCar = async(link: string) : Promise<void> => {
    await axios.delete(link, getAxiosConfig());
}

export const addCar = async(car: Car) : Promise<void> => {
    await axios.post(`${import.meta.env.VITE_API_URL}/api/cars`,
                        car,
                        getAxiosConfig()
    );   
}

export const updateCar = async(car: Car) => {
    const url = car._links?.self.href;
    if(!url){
        return;
    }
    const response = await axios.put(url,
                    car,
                    getAxiosConfig()
    );

    console.log('PUT RESPONSE:', response.data);
}