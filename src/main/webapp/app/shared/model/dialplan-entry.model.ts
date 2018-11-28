import { IPeer } from 'app/shared/model//peer.model';

export const enum MatchType {
    IP = 'IP',
    FROM_USER = 'FROM_USER',
    TO_USER = 'TO_USER',
    CONTACT_USER = 'CONTACT_USER',
    REQUEST_USER = 'REQUEST_USER',
    MANUAL = 'MANUAL'
}

export interface IDialplanEntry {
    id?: number;
    fromPeerMatchType?: MatchType;
    fromPeerMatchExp?: string;
    destination?: string;
    newDestination?: string;
    source?: string;
    newSource?: string;
    fromPeer?: IPeer;
}

export class DialplanEntry implements IDialplanEntry {
    constructor(
        public id?: number,
        public fromPeerMatchType?: MatchType,
        public fromPeerMatchExp?: string,
        public destination?: string,
        public newDestination?: string,
        public source?: string,
        public newSource?: string,
        public fromPeer?: IPeer
    ) {}
}
