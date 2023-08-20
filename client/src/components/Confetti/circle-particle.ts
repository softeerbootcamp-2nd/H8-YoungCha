import { colors } from './constant';
import { randomInRange } from './helper';

const gravityCircleParticles = 0.1;
const dragCircleParticles = 0.02;

export class CircleParticle {
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
    // CircleParticle의 속도를 적용합니다.
    this.velocity.x -= this.velocity.x * dragCircleParticles;
    this.velocity.y = this.velocity.y + gravityCircleParticles;

    // CircleParticle의 위치를 적용합니다.
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;
  }
}
