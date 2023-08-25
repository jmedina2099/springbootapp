import { Component, ViewEncapsulation, ElementRef, Input, OnInit, OnDestroy } from '@angular/core';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
    selector: 'jw-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['modal.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class ModalComponent implements OnInit, OnDestroy {
    @Input() id: string = 'emptyId';
    isOpen = false;
    private element: any;

    isCreateModal: boolean = false;

    constructor(private modalService: ModalService, private el: ElementRef) {
        this.element = el.nativeElement;
    }

    ngOnInit() {
        this.modalService.add(this);

        this.element.addEventListener('click', (el: any) => {
            if (el.target.className === 'jw-modal') {
                this.close();
            }
        });
    }

    ngOnDestroy() {
        this.modalService.remove(this);

        this.element.remove();
    }

    open() {
        this.element.style.display = 'block';
        document.body.classList.add('jw-modal-open');
        this.isOpen = true;
    }

    close() {
        this.element.style.display = 'none';
        document.body.classList.remove('jw-modal-open');
        this.isOpen = false;
    }
}