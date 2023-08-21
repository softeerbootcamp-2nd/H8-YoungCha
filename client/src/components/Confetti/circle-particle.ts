import { COLORS } from './constant';
import { randomInRange } from './helper';

const GRAVITY_CIRCLE_PARTICLES = 0.1;
const DRAG_CIRCLE_PARTICLES = 0.02;

export class CircleParticle {
  color: string;
  radius: number;
  position: { x: number; y: number };
  velocity: { x: number; y: number };

  constructor(canvas: HTMLCanvasElement) {
    this.color = COLORS[Math.floor(randomInRange(0, COLORS.length))].back;
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
    this.velocity.x -= this.velocity.x * DRAG_CIRCLE_PARTICLES;
    this.velocity.y = this.velocity.y + GRAVITY_CIRCLE_PARTICLES;

    // CircleParticle의 위치를 적용합니다.
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;
  }
}
