import { HttpClient, HttpErrorResponse, HttpHeaders, JsonpClientBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, throwError } from 'rxjs';
import { Device } from '../models/device';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DeviceService {


  device: Device | undefined;

  deviceUrl = 'http://localhost:8081/device';

  isEdit!: boolean;

  constructor(private http: HttpClient) { }

  // Observable string sources
  private emitChangeSource = new Subject<any>();
  // Observable string streams
  changeEmitted$ = this.emitChangeSource.asObservable();
  // Service message commands
  emitChange(change: any) {
    this.emitChangeSource.next(change);
  }

  getDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.deviceUrl + '/getAllDevices');
  }

  getDevice(id: number): Observable<Device> {
    const url = `${this.deviceUrl}/${id}`;
    console.log('>>>>>>>>>>>>>>>>' + url);
    return this.http.get<Device>(url);
  }

  saveDevice(device: Device): Observable<Device> {
    return this.http.post<Device>(this.deviceUrl+'/addDevice', device, httpOptions);
  }

  updateDevice(device: Device): Observable<Device> {
    const url = `${this.deviceUrl}/updateDevice/${device.id}`;
    console.log('JSON.stringify(device)>>>>>> ' + JSON.stringify(device));
    return this.http.put<Device>(url, device, httpOptions);
  }

  removeDevice(device: Device | number): Observable<Device> {
    const id = typeof device === 'number' ? device : device.id;
    const url = `${this.deviceUrl}/deleteDevice/${id}`;
    return this.http.delete<Device>(url, httpOptions);
  }


  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error.errorMessage}`);
    }
    // return an ErrorObservable with a user-facing error message
    // return new ErrorObservable replaced with throwError
    return throwError('Something bad happened; ' + error.error.errorMessage);
  }

}
