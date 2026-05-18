import { Injectable } from '@angular/core';
import { Task } from '../models/task';
import { TaskRepository } from '../repositories/task-repository';

@Injectable({
  providedIn: 'root'
})
export class LocalTaskService implements TaskRepository {

  private tasks: Task[] = [];

  getTasks(): Task[] {
    return this.tasks;
  }

  addTask(task: Task): void {
    this.tasks.push(task);
  }

  deleteTask(id: number): void {

    const index = this.tasks.findIndex(
        task => task.id === id
    );

    if (index !== -1) {
        this.tasks.splice(index, 1);
    }

    }

}