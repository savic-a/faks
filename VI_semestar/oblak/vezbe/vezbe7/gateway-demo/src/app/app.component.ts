import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(private http: HttpClient) {}
  title = 'gateway-demo';
  response = '';
  
  lambdaCors() {
    const url = 'https://il672o2amgmvzwz77igo6igbxm0ssoda.lambda-url.eu-central-1.on.aws/';
    this.http.get(url).subscribe(
      res => this.response = JSON.stringify(res, null, '\t')
    )
  }
}
