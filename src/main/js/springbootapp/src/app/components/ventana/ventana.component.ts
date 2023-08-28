import { Component } from '@angular/core';
import { Country } from 'src/app/entities/country/country';
import { FetchCountriesService } from 'src/app/services/fetch-countries/fetch-countries.service';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
  selector: 'ventana',
  templateUrl: './ventana.component.html',
  styleUrls: ['./ventana.component.css']
})
export class VentanaComponent {

  paises: Country[] | undefined;

  isCreateModal: boolean = false;

  paisDefault: Country = {
    id: 0,
    name: '',
    description: ''
  };

  paisEditar: Country = { ...this.paisDefault };  

  paisBorrar: Country = { ...this.paisDefault };  

  constructor(private service: FetchCountriesService, public modalService: ModalService, private loggerService: LoggerService){
  }

  ngOnInit() {
    this.service.fetchCountries()
      .subscribe( (data:Country[]) => {
        if( data ) {
          data.forEach( (pais: Country) => {
            this.loggerService.log(JSON.stringify(pais));
          });
          this.paises = data;
        }
      });
  }

  openModalCreate() {
    this.isCreateModal = true;
    this.paisEditar = { ...this.paisDefault };
    this.modalService.open('modal-1');
  }  

  openModalEdit(id: number) {
    this.isCreateModal = false;
    if( this.paises ) {
      this.paises.forEach( pais => {
        if( pais.id === id ) {
          this.paisEditar = { ...pais };
        }
      });
    }
    this.modalService.open('modal-1');
  }

  openModalEliminar(id: number) {
    if( this.paises ) {
      this.paises.forEach( pais => {
        if( pais.id === id ) {
          this.paisBorrar = pais;
        }
      });
    }
    this.modalService.open('modal-2');
  }  

  validate() {
    return ( this.paisEditar.name && this.paisEditar.name.trim() !== '' &&
             this.paisEditar.description && this.paisEditar.description.trim() !== '' );
  }

  save() {
    this.loggerService.log("Guardar" );
    if( this.paisEditar && ( this.isCreateModal || this.validate() ) ) {
      if( this.isCreateModal ) {
        this.service.createPais(this.paisEditar)
        .subscribe( (value: boolean) => {
          this.loggerService.log("createPais = "+value );
          this.ngOnInit();
        });
      } else {
        this.service.updatePais(this.paisEditar)
        .subscribe( (value: boolean) => {
          this.loggerService.log("updatePais = "+value );
          this.ngOnInit();
        })
      }
      this.modalService.close('modal-1');
    }
  }

  delete() {
    this.loggerService.log("Borrar" );
    if( this.paisBorrar ) {
      this.service.deletePais(this.paisBorrar)
      .subscribe( (value: boolean) => {
        this.loggerService.log("deletePais = "+value );
        this.ngOnInit();
      });
    }
    this.modalService.close('modal-2');
  }  

  closeModal(id: string) {
    this.modalService.close(id);
  }  
}