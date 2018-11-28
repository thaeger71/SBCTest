import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeer } from 'app/shared/model/peer.model';

@Component({
    selector: 'jhi-peer-detail',
    templateUrl: './peer-detail.component.html'
})
export class PeerDetailComponent implements OnInit {
    peer: IPeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ peer }) => {
            this.peer = peer;
        });
    }

    previousState() {
        window.history.back();
    }
}
