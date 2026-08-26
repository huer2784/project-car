export type CarApiResponse = {
    _embedded : {
            cars : Car[];
    };
}

export type Car = {
    brand: string;
    model: string;
    color: string;
    registationNumber: string;
    modelYear: number;
    price: number;
    _links?: {
        self: {
            href: string;
        },
        car: {
            href: string;
        },
        owner: {
            href: string;
        }
    };
}

