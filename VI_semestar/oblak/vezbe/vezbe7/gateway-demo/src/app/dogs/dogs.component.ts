import { Component } from "@angular/core";
import { DogsService } from "./dogs.service";

@Component({
    selector: 'app-dogs',
    templateUrl: './dogs.component.html'
})
export class DogsComponent {
    constructor(private dogsService: DogsService) {}
    response = 'The response will show up here';

    showDogs() {
        this.dogsService.getDogs().subscribe((data) =>
            this.response = JSON.stringify(data, null, '\t')
        )
    }

    addDog() {
        this.dogsService.addDog({name: 'German Shepherd', size: 'big', friendliness: 7}).subscribe((data) => 
            this.response = JSON.stringify(data, null, '\t')
        )
    }

    getDog() {
        this.dogsService.getDog('German Shepherd').subscribe((data) => 
            this.response = JSON.stringify(data, null, '\t')
        )
    }

    removeDog() {
        this.dogsService.removeDog('German Shepherd').subscribe((data) => 
            this.response = JSON.stringify(data, null, '\t')
        )
    }
}  