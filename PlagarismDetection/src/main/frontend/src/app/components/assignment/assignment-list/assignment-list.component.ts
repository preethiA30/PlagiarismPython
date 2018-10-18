import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../../../services/shared.service";
import {UserService} from "../../../services/user.service.client";
import {AssignmentService} from "../../../services/assignment.service.client";

@Component({
  selector: 'app-assignment-list',
  templateUrl: './assignment-list.component.html',
  styleUrls: ['./assignment-list.component.css']
})
export class AssignmentListComponent implements OnInit {

  user: any;
  userId: string;
  courseId: string;
  assignments = [];
  comparisonStrategy: string;
  successMessage: string;
  messageFlag: boolean;
  errorMessage: string;
  errorFlag: boolean;
  resultProgress: boolean;
  infoDropdown: boolean;


  constructor(private assignmentService: AssignmentService,
              private activatedRoute: ActivatedRoute,
              private sharedService: SharedService,
              private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
    this.comparisonStrategy = 'null';
    this.user = this.sharedService.user;
    this.userId = this.sharedService.userId;
    this.errorFlag = false;
    this.messageFlag = false;
    this.infoDropdown = false;

    this.activatedRoute.params.subscribe((params: any) => {
        this.courseId = params['cid'];
        console.log("assignment-list " + this.courseId);
      }
    );

    this.assignmentService.findAssignmentForCourse(this.courseId).subscribe((assignments) => {
      this.assignments = assignments;
      console.log(assignments);
    });
  }

  toggleInfoDropdown() {
    this.infoDropdown = !this.infoDropdown;
  }

  getResultCount(assignment: any) {
    return assignment.resultsId.length;
  }


  generateResults(assignment: any) {

    this.resultProgress = true;

    this.assignmentService.generateResults(this.userId, this.courseId, assignment.hexId,
      assignment.name, this.comparisonStrategy).subscribe((data: any) => {

      console.log(data);
      if (data.length > 0) {
        this.successMessage = "Results Generated! Click on assignment to view results.";
        this.messageFlag = true;
        this.router.navigate(['/user', 'course', this.courseId, 'assignment',
          assignment.hexId, 'result']);
      } else {
        this.errorMessage = "Error generating results!";
        this.errorFlag = true;
        this.resultProgress = false;
      }


    }, error => {
      console.log(error);
      this.errorMessage = "Error generating results!";
      this.errorFlag = true;

    });
  }

}
