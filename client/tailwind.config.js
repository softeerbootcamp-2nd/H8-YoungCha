/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        'main-hyundai-blue': ' #0E2B5C',
        'sub-active-blue': '#4CA7CE',
        'icon-yellow': '#FFA724',
        'tag-sky-blue': '#DEEBFF',
        'cool-grey-001': '#F2F4F7',
        'cool-grey-002': '#DBDEE3',
        'cool-grey-003': '#AEB1B7',
        'cool-grey-004': '#595C61',
        'cool-grey-black': '#202732',
      },
    },
  },
  plugins: [],
};
