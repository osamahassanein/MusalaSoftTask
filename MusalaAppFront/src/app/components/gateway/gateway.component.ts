import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Gateway } from 'src/app/models/gateway';
import { GatewayService } from 'src/app/services/gateway.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { GatewayformComponent } from '../gatewayform/gatewayform.component';

@Component({
  selector: 'app-gateway',
  templateUrl: './gateway.component.html',
  styleUrls: ['./gateway.component.css']
})
export class GatewayComponent implements OnInit {

  gateways!: Gateway[];

  // gatewayForm!: FormGroup;

  newGateway: Gateway = {
    id: 0,
    name: '',
    ipv4: ''
  };

  constructor(private gatewayService: GatewayService, //private fb: FormBuilder,
    private modalService: NgbModal) { 
      
    gatewayService.changeEmitted$.subscribe(emmitedOp => {
      this.gateways.unshift(emmitedOp);
    });
    }

  ngOnInit(): void {
    this.gatewayService.getGateways().subscribe(gateways => {
      this.gateways = gateways;
    });

    // this.gatewayForm = this.fb.group({
    //   name: [''],
    //   ipv4: ['']
    // });
  }

  onSubmit() {
    // this.gatewayService.saveGateway(this.gatewayForm.value).subscribe();
    this.ngOnInit();

  }

  openGatewayModal(gateway: Gateway) {
    if (gateway.id === 0) {
      this.gatewayService.isEdit = false;
    } else {
      this.gatewayService.isEdit = true;
    }
    // valid for both new and edit existing gatway
    this.gatewayService.gateway = gateway;
    this.modalService.open(GatewayformComponent);
  }

  removeGateway(gateway: Gateway) {
    if (gateway.devices?.length == 0) {
      if (confirm('Are you sure?')) {
        this.gatewayService.removeGateway(gateway).subscribe(() => {
          this.gateways.forEach((cur, index) => {
            if (gateway.id === cur.id) {
              this.gateways.splice(index, 1);
            }
          });
        });
      }
    } else alert('In order not to violate Referential integrity constraint, delete all related devices first');
  }

}
