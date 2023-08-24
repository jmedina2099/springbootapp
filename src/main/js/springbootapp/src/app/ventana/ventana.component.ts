import { Component } from '@angular/core';
import { FetchCountriesService } from '../fetch-countries/fetch-countries.service';
import { Country } from '../country/country';
import { ModalService } from '../modal-service/modal-service.service';

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

  constructor(private service: FetchCountriesService, private modalService: ModalService){
  }

  ngOnInit() {
    this.service.fetchCountries()
      .subscribe( (data:Country[]) => {
        if( data ) {
          data.forEach( (pais: Country) => {
            console.log(pais)
          });
          this.paises = data;
        }
      });
  }

  openModalEdit(id: number) {
    this.isCreateModal = false;
    if( this.paises ) {
      this.paises.forEach( pais => {
        if( pais.id === id ) {
          this.paisEditar = pais;
        }
      });
    }
    this.modalService.open('modal-1');
  } 

  validate() {
    return ( this.paisEditar.name && this.paisEditar.name !== '' &&
             this.paisEditar.description && this.paisEditar.description !== '' );
  }

  save() {
    console.log('Guardar');
    if( this.validate() ) {
      if( this.isCreateModal ) {
      } else {
        this.service.updatePais(this.paisEditar)
        .subscribe( () => {
          this.modalService.close('modal-1');
          this.ngOnInit();
        });
      }
    } else {
      this.modalService.open('modal-3');
    }
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }  
}