import plugin from 'tailwindcss/plugin';

const typography = plugin(function ({ addUtilities }) {
  addUtilities({
    '.title1': {
      'font-family': 'Hyndai Sans Head KR',
      'font-size': '22px',
      'font-weight': 500,
      'letter-spacing': '-0.66px',
    },
    '.title2': {
      'font-family': 'Hyndai Sans Head KR',
      'font-size': '22px',
      'font-weight': 500,
      'letter-spacing': '-0.6px',
    },
    '.title3': {
      'font-family': 'Hyndai Sans Head KR',
      'font-size': '18px',
      'letter-spacing': '-0.54px',
    },
    '.title4': {
      'font-family': 'Hyndai Sans Head KR',
      'font-size': '16px',
      'letter-spacing': '-0.48px',
    },
    '.title5': {
      'font-family': 'Hyndai Sans Head KR',
      'font-size': '14px',
      'letter-spacing': '-0.42px',
    },
    '.title6': {
      'font-family': 'Hyndai Sans Head KR',
      'font-size': '12px',
      'letter-spacing': '-0.36px',
    },

    '.body1': {
      'font-family': 'Hyndai Sans Text KR',
      'font-size': '16px',
      'letter-spacing': '-0.48px',
    },
    '.body2': {
      'font-family': 'Hyndai Sans Text KR',
      'font-size': '14px',
      'letter-spacing': '-0.42px',
    },
    '.body3': {
      'font-family': 'Hyndai Sans Text KR',
      'font-size': '12px',
      'letter-spacing': '-0.36px',
    },
    '.popup': {
      'font-family': 'Hyndai Sans Text KR',
      'font-size': '14px',
      'line-height': '150%',
      'letter-spacing': '-0.42px',
    },
  });
});

export default typography;
