import { TestBed } from '@angular/core/testing';

import { FetchCountriesService } from './fetch-countries.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('FetchCountriesService', () => {
  let service: FetchCountriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(FetchCountriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
