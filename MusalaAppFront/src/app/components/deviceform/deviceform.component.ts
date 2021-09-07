import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Device } from 'src/app/models/device';
import { Gateway } from 'src/app/models/gateway';
import { DeviceService } from 'src/app/services/device.service';

@Component({
  selector: 'app-deviceform',
  templateUrl: './deviceform.component.html',
  styleUrls: ['./deviceform.component.css']
})
export class DeviceformComponent implements OnInit {



  device!: Device;
  isEdit!: boolean;
  xBoolean = true;

  constructor(
    private deviceService: DeviceService,
    private modalService: NgbModal,
    public activeModal: NgbActiveModal,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.device = this.deviceService.device!;
    this.isEdit = this.deviceService.isEdit;
  }


  addDevice() {
    this.deviceService.saveDevice(this.device).subscribe(dv => {
      this.deviceService.emitChange(dv);
    });
    console.log('this.device >>>>> ' + this.device);
    // this.ngOnInit();
    this.activeModal.dismiss('Cross click');
  }

  updateDevice(device: Device) {
    this.deviceService.updateDevice(device).subscribe(
      res => {
        console.log('JSON.stringify(res)>>>> ' + JSON.stringify(res));
        const id = res['id'];
        // this.router.navigate(['/device', id]);
        // this.ngOnInit();

      },
      err => {
        console.log(err);
      }
    );
    this.activeModal.dismiss('Cross click');
  }

}
