import plugin from 'tailwindcss/plugin';

function range(start, stop, step = 1) {
  return Array.from(
    { length: (stop - start) / step + 1 },
    (_, i) => start + i * step
  );
}

function pxToRem(px, base = 16) {
  return `${px / base}rem`;
}

function px(start = 0, stop = 200, step = 1) {
  return {
    ...range(start, stop, step).reduce((acc, px) => {
      acc[`${px}px`] = pxToRem(px);
      return acc;
    }, {}),
  };
}

const pixel = plugin(null, {
  theme: {
    extend: {
      spacing: px(-128, 1280),
      borderWidth: px(0, 10),
      borderRadius: px(0, 16),
      fontSize: px(0, 64, 2),
    },
  },
});

export default pixel;
