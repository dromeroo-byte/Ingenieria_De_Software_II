import { Component } from '@angular/core';
import { Task } from '../../models/task';
import { LocalTaskService } from '../../services/local-task.service';

@Component({
  selector: 'app-task-list',
  standalone: false,
  templateUrl: './task-list.html'
})
export class TaskList {

  tasks: Task[] = [];

  title: string = '';

  description: string = '';

  constructor(
    private taskService: LocalTaskService
  ) {}

  ngOnInit() {
    this.tasks = this.taskService.getTasks();
  }

  addTask() {

    if (!this.title.trim()) {
      return;
    }

    const task: Task = {

      id: Date.now(),

      title: this.title,

      description: this.description,

      createdAt: new Date()

    };

    this.taskService.addTask(task);

    this.title = '';
    this.description = '';

  }

  deleteTask(id: number) {
  this.taskService.deleteTask(id);
}

}