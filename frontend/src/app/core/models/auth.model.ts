import { User } from "./user.model";

export interface LoginRequest{
    username: string;
    password: string;
}

export interface RegisterRequest{
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    timezone: string;
}

export interface AuthResponse{
    user: User;
    token: string;
    refresh: string;
}

export interface RefreshRequest{
    refreshToken: string
}