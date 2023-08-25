import { Injectable } from '@angular/core';
import { ModalComponent } from 'src/app/components/modal/modal.component';

@Injectable({ providedIn: 'root' })
export class ModalService {
    public modals: ModalComponent[] = [];

    add(modal: ModalComponent) {
        // ensure component has a unique id attribute
        if (!modal.id) {
            throw new Error('modal must have a unique id attribute1' );
        }
        if (this.modals.find(x => x.id === modal.id)) {
            throw new Error('modal must have a unique id attribute2' );
        }

        // add modal to array of active modals
        this.modals.push(modal);
    }

    remove(modal: ModalComponent) {
        // remove modal from array of active modals
        this.modals = this.modals.filter(x => x === modal);
    }

    open(id: string) {
        // open modal specified by id
        const modal = this.modals.find(x => x.id === id);

        if (!modal) {
            throw new Error(`modal '${id}' not found`);
        }

        modal.open();
    }

    close(id:string) {
        // close the modal that is currently open
        const modal = this.modals.find(x => x.id === id);
        modal?.close();
    }
}