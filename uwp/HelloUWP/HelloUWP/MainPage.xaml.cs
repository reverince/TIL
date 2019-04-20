using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// 빈 페이지 항목 템플릿에 대한 설명은 https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x412에 나와 있습니다.

namespace HelloUWP
{
    /// <summary>
    /// 자체적으로 사용하거나 프레임 내에서 탐색할 수 있는 빈 페이지입니다.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
        }

        private void AppBarButton_Click(object sender, RoutedEventArgs e)
        {
            Frame frame = frameMain;
            switch (((AppBarButton)sender).Label)
            {
                case "A":
                    frame.Navigate(typeof(DefaultPage));
                    break;
                case "B":
                    frame.Navigate(typeof(BlankPage1));
                    break;
                case "대하여":
                    frame.Navigate(typeof(DefaultPage));
                    break;
                default:
                    break;
            }
        }

        private void NavigationViewControl_SelectionChanged(NavigationView sender, NavigationViewSelectionChangedEventArgs args)
        {
            Frame frame = frameMain;
            NavigationViewItem item = args.SelectedItem as NavigationViewItem;
            switch (item.Content)
            {
                case "A":
                    frame.Navigate(typeof(DefaultPage));
                    break;
                case "B":
                    frame.Navigate(typeof(BlankPage1));
                    break;
            }
        }
    }
}
