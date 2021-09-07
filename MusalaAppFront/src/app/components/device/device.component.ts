import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Device } from 'src/app/models/device';
import { Gateway } from 'src/app/models/gateway';
import { DeviceService } from 'src/app/services/device.service';
import { GatewayService } from 'src/app/services/gateway.service';
import { DeviceformComponent } from '../deviceform/deviceform.component';

@Component({
  selector: 'app-device',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.css']
})
export class DeviceComponent implements OnInit {
  devices!: Device[];

  // gatewayForm!: FormGroup;

  newDevice: Device = {
    id: 0,
    vendor: '',
    creationDate: new Date(),
    status: '',
    gateway: new Gateway()
  };

  constructor(private gatewayService: GatewayService, private deviceService: DeviceService, //private fb: FormBuilder,
    private modalService: NgbModal,
    private route: ActivatedRoute) {

    deviceService.changeEmitted$.subscribe(emmitedOp => {
      this.devices.unshift(emmitedOp);
    });
  }

  ngOnInit(): void {

    const id = Number(this.route.snapshot.paramMap.get('id')); //+this.route.snapshot.paramMap.get('id');
    console.log('ID>>>>>> ' + id);
    this.gatewayService.getGateway(id).subscribe(gateway => {
      // console.log('JSON.stringify(gateway) >>> '+JSON.stringify(gateway));
      // console.log('JSON.stringify(gateway.devices) >>> '+JSON.stringify(gateway.devices));
      this.devices = gateway.devices!;
      this.newDevice.gateway = gateway;
    });
    // this.deviceService.getDevices(id).subscribe(devices => {
    //   this.devices = devices;
    // });
  }

  openDeviceModal(device: Device) {
    if (device.id === 0) {
      this.deviceService.isEdit = false;
    } else {
      this.deviceService.isEdit = true;
    }
    console.log('JSON.stringify(device)>>>  ' + JSON.stringify(device));
    // valid for both new and edit existing device
    this.deviceService.device = device;
    this.modalService.open(DeviceformComponent);
  }

  removeDevice(device: Device) {
    if (confirm('Are you sure?')) {
      this.deviceService.removeDevice(device).subscribe(() => {
        this.devices.forEach((cur, index) => {
          if (device.id === cur.id) {
            this.devices.splice(index, 1);
          }
        });
      });
    }

  }

}
