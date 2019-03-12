% For Prac09
function y = sinegen(A, f, phi, tdur)
t = 0 : 0.01 : tdur;
y = A * sin(2 * pi * f * t + phi);
plot(t, y, 'r-');
