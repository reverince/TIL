clf;
t = 0 : 0.01 : 1;
y = 1.5 * cos(pi*t) .* sin(10*pi*t);
plot(t, y);
axis([ 0 1 -1.6 1.6 ]);
xlabel('radian');
ylabel('y(t)');
title('예제 5 복잡한 정현파 그리기');
grid;
