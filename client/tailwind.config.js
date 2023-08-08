/** @type {import('tailwindcss').Config} */
import pixel from './plugins/pixel';
import typography from './plugins/typography';

export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        'main-blue': ' #0E2B5C',
        'sub-blue': '#4CA7CE',
        'icon-yellow': '#FFA724',
        'tag-skyblue': '#DEEBFF',
        'grey-001': '#F2F4F7',
        'grey-002': '#DBDEE3',
        'grey-003': '#AEB1B7',
        'grey-004': '#595C61',
        'grey-black': '#202732',
      },
    },
    fontFamily: {
      'hsans-head': ['Hyundai Sans Head KR', 'sans-serif'],
      'hsans-text': ['Hyundai Sans Text KR', 'sans-serif'],
    },
  },

  plugins: [typography, pixel],
};
