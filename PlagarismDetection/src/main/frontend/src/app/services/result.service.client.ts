import {Injectable} from '@angular/core';
import { Http, Response } from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable()

export class ResultService {

  baseUrl = environment.baseUrl;

  constructor(private http: Http) { }

  api = {
    'findResultsForAssignment' : this.findResultsForAssignment,
    'findResultById' : this.findResultById
  };

  findResultsForAssignment(assignmentId: String) {
    const url = this.baseUrl + '/api/assignment/' + assignmentId + '/result';
    console.log("IN RESULTS CLIENT");
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }

  findResultById(resultId: String) {
    const url = this.baseUrl + '/api/assignment/result/' + resultId;
    console.log("IN RESULTS CLIENT");
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }


}

