import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { VentanaComponent } from './ventana.component';
import { ModalComponent } from '../modal/modal.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { FetchCountriesService } from 'src/app/services/fetch-countries/fetch-countries.service';
import { of } from 'rxjs';
import { ModalService } from 'src/app/services/modal/modal.service';

describe('VentanaComponent', () => {
  let ventana: VentanaComponent;
  let fixtureVentana: ComponentFixture<VentanaComponent>;

  let fetchCountriesService: FetchCountriesService;
  let modalService: ModalService;

  beforeEach( () => {
    TestBed.configureTestingModule({
      declarations: [VentanaComponent,ModalComponent],
      imports: [HttpClientTestingModule,FormsModule],
      providers: [ModalService,{
        provide: FetchCountriesService,
        useValue: jasmine.createSpyObj('FetchCountriesService', {
          fetchCountries: of([{id:10,name:'USA',description:'Neighbor at top'}]),
          updatePais: of(true),
          createPais: of(true),
          deletePais: of(true)
        })
      }],
    });
    fixtureVentana = TestBed.createComponent(VentanaComponent);
    ventana = fixtureVentana.componentInstance;
    fetchCountriesService = TestBed.inject(FetchCountriesService);
    modalService = TestBed.inject(ModalService);
    fixtureVentana.detectChanges();
  });

  it('should create', () => {
    expect(ventana).toBeTruthy();
  });

  it('should have add button', () => {
    let button = fixtureVentana.debugElement.nativeElement.querySelector('.addButton');
    expect(button.textContent).toContain('Agrega país');
  });

  it('should have countries table', waitForAsync(() => {
    let table = fixtureVentana.debugElement.nativeElement.querySelector('.contentListado');
    expect(table).not.toBeNull();

    ventana.ngOnInit();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(fetchCountriesService.fetchCountries).toHaveBeenCalled();
      let name = fixtureVentana.debugElement.nativeElement.querySelector('.span1');
      expect(name.innerHTML).toContain('USA');
      let description = fixtureVentana.debugElement.nativeElement.querySelector('.span2');
      expect(description.innerHTML).toContain('Neighbor at top');
    });
  }));

  it('should click add button does open modal', waitForAsync(() => {
    spyOn(modalService, 'open');

    let button = fixtureVentana.debugElement.nativeElement.querySelector('.addButton');
    expect(button).toBeTruthy();

    button.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(modalService.open).toHaveBeenCalled();
    });
  }));

  it('should click edit button does open modal', waitForAsync(() => {
    spyOn(modalService, 'open');

    ventana.ngOnInit();
    fixtureVentana.detectChanges();

    let buttonEdit = fixtureVentana.debugElement.nativeElement.querySelector('.editar');
    expect(buttonEdit).toBeTruthy();

    buttonEdit.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(modalService.open).toHaveBeenCalled();
    });
  }));

  it('should click delete button does open modal', waitForAsync(() => {
    spyOn(modalService, 'open');

    ventana.ngOnInit();
    fixtureVentana.detectChanges();

    let buttonEdit = fixtureVentana.debugElement.nativeElement.querySelector('.eliminar');
    expect(buttonEdit).toBeTruthy();

    buttonEdit.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(modalService.open).toHaveBeenCalled();
    });
  })); 

  it('should save button from edit can be click', waitForAsync(() => {
    ventana.ngOnInit();
    fixtureVentana.detectChanges();

    let buttonEdit = fixtureVentana.debugElement.nativeElement.querySelector('.editar');
    expect(buttonEdit).toBeTruthy();

    buttonEdit.click();
    fixtureVentana.detectChanges();

    let buttonSave = fixtureVentana.debugElement.nativeElement.querySelector('.guardar');
    expect(buttonSave).toBeTruthy();

    buttonSave.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(fetchCountriesService.updatePais).toHaveBeenCalled();
    });
  })); 
  
  it('should save button from create can be click', waitForAsync(() => {
    let button = fixtureVentana.debugElement.nativeElement.querySelector('.addButton');
    expect(button).toBeTruthy();

    button.click();
    fixtureVentana.detectChanges();

    let buttonSave = fixtureVentana.debugElement.nativeElement.querySelector('.guardar');
    expect(buttonSave).toBeTruthy();

    buttonSave.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(fetchCountriesService.createPais).toHaveBeenCalled();
    });
  }));
  
  it('should delete button can be click', waitForAsync(() => {
    ventana.ngOnInit();
    fixtureVentana.detectChanges();

    let buttonEliminar = fixtureVentana.debugElement.nativeElement.querySelector('.eliminar');
    expect(buttonEliminar).toBeTruthy();

    buttonEliminar.click();
    fixtureVentana.detectChanges();

    let buttonDelete = fixtureVentana.debugElement.nativeElement.querySelector('.delete');
    expect(buttonDelete).toBeTruthy();

    buttonDelete.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(fetchCountriesService.deletePais).toHaveBeenCalled();
    });
  })); 

  it('should click close button does close modal', waitForAsync(() => {
    spyOn(modalService, 'close');

    let button = fixtureVentana.debugElement.nativeElement.querySelector('.addButton');
    expect(button).toBeTruthy();

    button.click();
    fixtureVentana.detectChanges();

    let buttonCloseModal = fixtureVentana.debugElement.nativeElement.querySelector('.closeModal');
    expect(buttonCloseModal).toBeTruthy();
    
    buttonCloseModal.click();
    fixtureVentana.detectChanges();

    fixtureVentana.whenStable().then(() => {
      expect(modalService.close).toHaveBeenCalled();
    });
  }));  

});
