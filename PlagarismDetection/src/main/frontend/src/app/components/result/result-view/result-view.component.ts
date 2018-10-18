import {Component, OnInit} from '@angular/core';
import {ResultService} from "../../../services/result.service.client";
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../../../services/shared.service";

@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {
  courseId: string;
  assignmentId: string;
  resultId: string;
  result: any;
  student1: string;
  student2: string;
  snippets = [];

  constructor(private resultService: ResultService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((params: any) => {
      this.courseId = params['cid'];
      this.assignmentId = params['aid'];
      this.resultId = params['rid'];
      console.log('Result ID: ' + this.resultId);
    });

    this.resultService.findResultById(this.resultId).subscribe((result) => {
      this.result = result;
      console.log(result);
      console.log(this.result.snippets);
      this.student1 = this.result['student1'];
      this.student2 = this.result['student2'];
      this.snippets = this.result.snippets;
    });

  }

  getFileName(code: string) {
    let code1 = "<Filename>two.py</Filename><lines><mark>def diff(a, b):</mark> return a + b</lines>";

    let indexFilename = code.indexOf("<Filename>");
    let indexEndFilename = code.indexOf("</Filename>");

    let filename = code.substring(indexFilename + 10, indexEndFilename);

    // let filename = code1.split("<Filename>","</Filename>",1);
    console.log(filename);
    return filename;
  }

  getFirstLine(code: string) {
    let lineOneStart = code.indexOf("<lines>");

    let lineOneEnd = code.indexOf("<mark>");

    return code.substring(lineOneStart + 7, lineOneEnd);
  }

  getHighlighted(code: string) {
    let lineOneEnd = code.indexOf("<mark>");

    let highlightEnd = code.indexOf("</mark>");

    return code.substring(lineOneEnd + 6, highlightEnd);
  }

  getLastLine(code: string) {
    let highlightEnd = code.indexOf("</mark>");

    let lineTwoEnd = code.indexOf("</lines>");

    return code.substring(highlightEnd + 7, lineTwoEnd);
  }

}
