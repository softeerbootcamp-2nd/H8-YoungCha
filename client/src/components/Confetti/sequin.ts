import { colors } from './constant';
import { randomInRange } from './helper';

const gravitySequins = 0.1;
const dragSequins = 0.02;

export class Sequin {
  color: string;
  radius: number;
  position: { x: number; y: number };
  velocity: { x: number; y: number };

  constructor(canvas: HTMLCanvasElement) {
    this.color = colors[Math.floor(randomInRange(0, colors.length))].back;
    this.radius = randomInRange(2, 6);
    this.position = {
      x: [
        randomInRange(200, 400),
        randomInRange(canvas.width - 400, canvas.width - 200),
      ][Math.floor(Math.random() + 0.5)],
      y: randomInRange(canvas.height - 100, canvas.height),
    };
    this.velocity = {
      x: randomInRange(-25, 25),
      y: randomInRange(-10, -15),
    };
  }

  update(): void {
    // Sequin의 속도를 적용합니다.
    this.velocity.x -= this.velocity.x * dragSequins;
    this.velocity.y = this.velocity.y + gravitySequins;

    // Sequin의 위치를 적용합니다.
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;
  }
}
