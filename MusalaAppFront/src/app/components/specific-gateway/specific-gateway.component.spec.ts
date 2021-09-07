import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificGatewayComponent } from './specific-gateway.component';

describe('SpecificGatewayComponent', () => {
  let component: SpecificGatewayComponent;
  let fixture: ComponentFixture<SpecificGatewayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpecificGatewayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecificGatewayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
