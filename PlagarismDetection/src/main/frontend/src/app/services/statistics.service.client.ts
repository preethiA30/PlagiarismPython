import {Injectable} from '@angular/core';
import { Http, Response } from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable()

export class StatisticsService {

  baseUrl = environment.baseUrl;

  constructor(private http: Http) { }

  api = {
    'getStatistics' : this.getStatistics,
  };

  getStatistics() {
    const url = this.baseUrl + '/api/statistics';
    return this.http.get(url)
      .map((response: Response) => {
        console.log(response.json());
        return response.json();
      });
  }

}
