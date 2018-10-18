import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service.client';
import {ActivatedRoute, Router} from '@angular/router';
import {SharedService} from '../../../services/shared.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {

  userId: string;
  user: any;
  username: string;
  password: string;
  verifyPassword: string;
  gitHubUsername: string;
  gitHubPassword: string;
  verifyGitHubPassword: string;
  lastName: string;
  email: string;
  phoneNumber: string;


  constructor(private userService: UserService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private sharedService: SharedService) {
  }

  ngOnInit() {
    this.user = this.sharedService.user;
    console.log(this.user);
    this.userId = this.user['id'];
    console.log(this.user);
    this.username = this.user['username'];
    this.gitHubUsername = this.user['gitUsername'];
    console.log(this.gitHubUsername);
    this.lastName = this.user['lastName'];
    this.email = this.user['email'];
    this.phoneNumber = this.user['phone'];
  }


}
