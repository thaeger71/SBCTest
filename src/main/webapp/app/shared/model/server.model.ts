import { IPeer } from 'app/shared/model//peer.model';

export const enum Transport {
    UDP = 'UDP',
    TCP = 'TCP',
    TLS = 'TLS'
}

export interface IServer {
    id?: number;
    address?: string;
    port?: number;
    transport?: Transport;
    peers?: IPeer[];
}

export class Server implements IServer {
    constructor(public id?: number, public address?: string, public port?: number, public transport?: Transport, public peers?: IPeer[]) {}
}
