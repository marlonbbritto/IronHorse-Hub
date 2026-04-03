import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MotoFormComponent } from './components/moto-form/moto-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MotoFormComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class AppComponent {
  title = 'frontend';
}
