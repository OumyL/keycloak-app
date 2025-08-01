import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';


@Component({
  selector: 'app-orders',
  standalone: false,
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit{
  public orders : any;
  constructor(private http : HttpClient, private router : Router) {
  }
  ngOnInit() {
    this.http.get("http://localhost:8088/api/orders").subscribe({
      next : orders => {
        console.log('ORDERS:', orders);
        this.orders = orders;
      },
      error : err => {
        console.log(err);
      }
    });
  }

  getOrderDetails(o:any) {
    this.router.navigateByUrl("/order-details/"+o.id);


  }
}
