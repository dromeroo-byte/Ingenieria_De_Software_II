import { Task } from '../models/task';

export interface TaskRepository {

  getTasks(): Task[];

  addTask(task: Task): void;

  deleteTask(id: number): void;

}