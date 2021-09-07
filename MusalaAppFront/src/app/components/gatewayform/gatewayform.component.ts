import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Gateway } from 'src/app/models/gateway';
import { GatewayService } from 'src/app/services/gateway.service';

@Component({
  selector: 'app-gatewayform',
  templateUrl: './gatewayform.component.html',
  styleUrls: ['./gatewayform.component.css']
})
export class GatewayformComponent implements OnInit {

  gateway!: Gateway;
  isEdit!: boolean;
  xBoolean = true;

  constructor(
    private gatewayService: GatewayService,
    private modalService: NgbModal,
    public activeModal: NgbActiveModal,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.gateway = this.gatewayService.gateway;
    this.isEdit = this.gatewayService.isEdit;
  }


  addGateway() {
    this.gatewayService.saveGateway(this.gateway).subscribe(gw => {
      this.gatewayService.emitChange(gw);
    });
    console.log('this.gateway >>>>> ' + this.gateway);
    this.ngOnInit();

    this.activeModal.dismiss('Cross click');
  }

  updateGateway(gateway: Gateway) {
    this.gatewayService.updateGateway(gateway).subscribe(
      //this.http.put<Gateway>('/updateGateway/' + gateway.id, gateway).subscribe(
      res => {
        const id = res['id'];
        this.router.navigate(['/gateway', id]);
      },
      err => {
        console.log(err);
      }
    );
    this.activeModal.dismiss('Cross click');
  }

}
