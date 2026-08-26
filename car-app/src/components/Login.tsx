import axios from "axios";
import React, { useState } from "react";
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Carlist from "./Carlist";
import { Snackbar } from "@mui/material";

type User = {
    username: string;
    password: string;
}

const Login = () => {
    const [user, setUser] = useState<User>({
        username: '',
        password: ''
    });

    const [isAuth, setAuth] = useState(false);
    
    const [open, setOpen] = useState(false);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUser({
            ...user,
            [event.target.name] : event.target.value
        });
    }

    const logout = () => {
        setAuth(false);
        //sessionStorage.setItem("jwt","");
        sessionStorage.removeItem("jwt");
    }

    const handleLogin = () => {
        axios.post(`${import.meta.env.VITE_API_URL}/login`,
                                    user,
                                    {
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    }

        ).then(res => {
            const jwtToken = res.headers.authorization;
            if(jwtToken !== null){
                sessionStorage.setItem("jwt", jwtToken);
                setAuth(true);
            }
        }).catch((err) => {
            setOpen(true);
            console.log(err);
        });
    }
    /*
    const handleLogin = async () => {
        try {
            const res = await axios.post(
                `${import.meta.env.VITE_API_URL}/login`,
                user,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );

            const jwtToken = res.headers.authorization;

            if (jwtToken !== null) {
                sessionStorage.setItem("jwt", jwtToken);
                setAuth(true);
            }
        } catch (err) {
            console.log(err);
        }
    };
    */

    
    
    if(isAuth){
        return <Carlist logout={logout}/>
    }else{
        return (
            <>
                <Stack spacing={2} sx={{mt:2, alignItems: "center"}}>
                    <TextField label="Username" name="username" onChange={handleChange}></TextField>
                    <TextField label="Password" name="password" type="password" onChange={handleChange}></TextField>
                    <Button variant="outlined" color="primary" onClick={handleLogin}>로그인</Button>
                </Stack>
                <Snackbar
                open={open}
                autoHideDuration={3000}
                onClose={() => setOpen(false)}
                message="로그인 실패"
                />
            </>
        );
    }
}

export default Login;