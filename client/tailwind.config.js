/** @type {import('tailwindcss').Config} */
import pixel from 'tailwindcss-pixel';
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
        'best-red': '#FF4D4D',
      },
      keyframes: {
        fade: {
          '0%': { opacity: '0' },
          '20%': { opacity: '1' },
          '40%': { opacity: '1' },
          '60%': { opacity: '0' },
          '100%': { opacity: '0' },
        },
        good: {
          from: { transform: 'scale(0)' },
          to: { transform: 'scale(1)' },
        },
      },
      animation: {
        fade: 'fade 1s ease-in-out infinite ',
        good: 'good 0.5s ease-in-out forwards',
      },
    },
    fontFamily: {
      'hsans-head': ['Hyundai Sans Head KR', 'sans-serif'],
      'hsans-text': ['Hyundai Sans Text KR', 'sans-serif'],
    },
  },

  plugins: [typography, pixel({ rem: true, extend: true, suffix: 'px' })],
};
