/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Plantillas Thymeleaf
    './src/**/*.{js,jsx,ts,tsx}',               // JS y otros archivos
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

