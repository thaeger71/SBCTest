export const enum Transport {
    UDP = 'UDP',
    TCP = 'TCP',
    TLS = 'TLS'
}

export interface IRegistration {
    id?: number;
    remoteAddress?: string;
    remotePort?: number;
    expires?: number;
    userAgent?: string;
    contact?: string;
    transport?: Transport;
}

export class Registration implements IRegistration {
    constructor(
        public id?: number,
        public remoteAddress?: string,
        public remotePort?: number,
        public expires?: number,
        public userAgent?: string,
        public contact?: string,
        public transport?: Transport
    ) {}
}
