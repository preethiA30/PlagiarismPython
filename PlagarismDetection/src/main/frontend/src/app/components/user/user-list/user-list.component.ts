import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services/user.service.client";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users = [];

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {

    this.userService.findAllUsers().subscribe((users) => {
      this.users = users;
      console.log(users);
    });
  }

  deleteUser(userId: string) {
    this.userService.deleteUser(userId).subscribe((res) => {
      console.log(res);
      this.ngOnInit();
    }, error => {
      console.log(error);
    })
  }

}
