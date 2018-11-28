export const enum Transport {
    UDP = 'UDP',
    TCP = 'TCP',
    TLS = 'TLS'
}

export interface IPeer {
    id?: number;
    description?: string;
    user?: string;
    authUser?: string;
    secret?: string;
    address?: string;
    port?: number;
    transport?: Transport;
    register?: boolean;
    qualify?: number;
}

export class Peer implements IPeer {
    constructor(
        public id?: number,
        public description?: string,
        public user?: string,
        public authUser?: string,
        public secret?: string,
        public address?: string,
        public port?: number,
        public transport?: Transport,
        public register?: boolean,
        public qualify?: number
    ) {
        this.register = this.register || false;
    }
}
