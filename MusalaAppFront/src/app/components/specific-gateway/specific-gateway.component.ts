import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Gateway } from 'src/app/models/gateway';
import { GatewayService } from 'src/app/services/gateway.service';

@Component({
  selector: 'app-specific-gateway',
  templateUrl: './specific-gateway.component.html',
  styleUrls: ['./specific-gateway.component.css']
})
export class SpecificGatewayComponent implements OnInit {

  gateway!: Gateway;

  constructor(private gatewayService: GatewayService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id')); //+this.route.snapshot.paramMap.get('id');
      console.log('ID>>>>>> '+id);
    this.gatewayService.getGateway(id).subscribe(gateway => {
      this.gateway = gateway;
    });
  }

}
