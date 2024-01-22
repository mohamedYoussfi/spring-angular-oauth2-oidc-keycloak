import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{

  public customers : any;

  constructor(private http: HttpClient) {
  }
  ngOnInit() {
    this.http.get("http://localhost:8087/api/customers")
      .subscribe({
        next : data => {
          this.customers = data;
        },
        error : err => {
          console.log(err);
        }
      })
  }

}
