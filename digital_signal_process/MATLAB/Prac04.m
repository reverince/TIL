t = 0 : 0.01 : 6*pi;
y = 2 + cos(t);
plot(t, y);
axis([ 0 6*pi -3 3 ]);
xlabel('radian');
ylabel('y(t)');
title('정현파 그리기');
grid;
