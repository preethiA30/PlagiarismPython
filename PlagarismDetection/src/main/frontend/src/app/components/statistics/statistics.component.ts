import {Component, OnInit} from '@angular/core';
import {StatisticsService} from "../../services/statistics.service.client";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  stats = [];

  constructor(private statisticsService: StatisticsService) {
  }

  ngOnInit() {

    this.statisticsService.getStatistics().subscribe((stats) => {
      this.stats = stats;
    })
  }

}
