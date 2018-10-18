import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SharedService} from "../../../services/shared.service";
import {ResultService} from "../../../services/result.service.client";

@Component({
  selector: 'app-result-list',
  templateUrl: './result-list.component.html',
  styleUrls: ['./result-list.component.css']
})
export class ResultListComponent implements OnInit {

  user: any;
  userId: string;
  courseId: string;
  results = [];
  assignmentId: string;


  constructor(private resultService: ResultService,
              private activatedRoute: ActivatedRoute,
              private sharedService: SharedService) {
  }

  ngOnInit() {
    this.user = this.sharedService.user;
    this.userId = this.sharedService.userId;

    this.activatedRoute.params.subscribe((params: any) => {
        this.courseId = params['cid'];
        this.assignmentId = params['aid'];
        console.log("Course Id: " + this.courseId);
      }
    );

    this.resultService.findResultsForAssignment(this.assignmentId).subscribe((results) => {
      for (let i = 0; i < results.length; i++) {
        this.results[i] = JSON.parse(results[i]);
      }
      console.log(this.results)
    });
  }

}
