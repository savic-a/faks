import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { WineService } from 'src/app/service/wine.service';
import { Wine } from '../wine/wine.component';

@Component({
  selector: 'app-create-wine',
  templateUrl: './create-wine.component.html',
  styleUrls: ['./create-wine.component.css'],
})
export class CreateWineComponent implements OnInit {
  wine: Wine = {
    _id: 0,
    name: '',
    year: 0,
    grapes: '',
    country: '',
    region: '',
  };

  @Input() itemFromParent = '';   // pozdrav iz parent componente  OD RODITELJA KA DETETU
  @Output() newItemEvent = new EventEmitter<string>();  // ZA SLANJE OD DETETA KA RODITELJU
  valueFromHome = '';
  itemToParent = '';  // ZA SLANJE OD DETETA KA RODITELJU

  constructor(private wineService: WineService, private router: Router) {}

  ngOnInit(): void {
    this.wineService.selectedValue$.subscribe((value) => {
      this.valueFromHome = value;
    });
  }

  add() {
    this.wineService.add(this.wine).subscribe((res: any) => {
      console.log(res);
      this.router.navigate(['wine']);
    });
  }

  sendToParent() {
    this.newItemEvent.emit(this.itemToParent);
  }
}
