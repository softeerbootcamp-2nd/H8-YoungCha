import { colors } from './constant';
import { randomInRange } from './helper';

const gravityParticles = 0.1;
const dragParticles = 0.075;
const TERMINAL_VELOCITY = 3;

type RangeType = [number, number];

export class Particle {
  randomModifier: number;
  color: { front: string; back: string };
  dimensions: { x: number; y: number };
  position: { x: number; y: number };
  rotation: number;
  scale: { x: number; y: number };
  velocity: { x: number; y: number };

  constructor(canvas: HTMLCanvasElement) {
    this.randomModifier = randomInRange(0, 99);
    this.color = colors[Math.floor(randomInRange(0, colors.length))];
    this.dimensions = {
      x: randomInRange(5, 9),
      y: randomInRange(8, 15),
    };
    this.position = {
      x: [
        randomInRange(200, 400),
        randomInRange(canvas.width - 400, canvas.width - 200),
      ][Math.floor(Math.random() + 0.5)],
      y: randomInRange(canvas.height, canvas.height),
    };
    this.rotation = randomInRange(0, 2 * Math.PI);
    this.scale = {
      x: 1,
      y: 1,
    };
    this.velocity = initParticleVelocity([-40, 40], [10, 22]);
  }

  update(): void {
    // Particles의 속도를 적용합니다.
    this.velocity.x -= this.velocity.x * dragParticles;
    this.velocity.y = Math.min(
      this.velocity.y + gravityParticles,
      TERMINAL_VELOCITY
    );
    this.velocity.x += Math.random() > 0.5 ? Math.random() : -Math.random();

    // Particles의 위치를 적용합니다.
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;

    // Particles의 회전을 적용합니다.
    this.scale.y = Math.cos((this.position.y + this.randomModifier) * 0.09);
  }
}

/**
 * @description
 * Particle의 초기 속도를 설정합니다.
 */
function initParticleVelocity(xRange: RangeType, yRange: RangeType) {
  const x = randomInRange(xRange[0], xRange[1]);
  const range = yRange[1] - yRange[0] + 1;
  let y =
    yRange[1] -
    Math.abs(randomInRange(0, range) + randomInRange(0, range) - range);

  // Particle가 최대 범위보다 높아지는 경우
  if (y >= yRange[1] - 1) {
    y += Math.random() < 0.25 ? randomInRange(1, 3) : 0;
  }
  return { x: x, y: -y };
}
